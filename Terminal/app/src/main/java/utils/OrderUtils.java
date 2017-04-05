package utils;

import android.util.Log;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interfaces.OrderService;
import pojo.order.DataItem;
import pojo.order.Order;
import pojo.order.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by Lvmoy on 2017/4/4 0004.
 * Mode: - - !
 */

public class OrderUtils {
    private static boolean isConnect = true;
    private static List<DataItem> dataItems = new ArrayList<>();
    public static boolean doCatPing(String string){
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        boolean connected = false;
        try {
            String str = "ping -c 1 -i 0.2 -W 1 " + string;
            process = runtime.exec(str);
            int result = process.waitFor();
            if(result == 0){
                runtime = null;
                process.destroy();
                connected =  true;
            }else {
                runtime = null;
                process.destroy();
                connected = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connected;
    }

    public static boolean do570Ping(String ipStr){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(LoganSquareConverterFactory.create())
                .baseUrl(ipStr)
                .build();

        OrderService orderService =  retrofit.create(OrderService.class);
        Call<Result> call = orderService.reportOrder(new Order(2, 2, 5, "570 PING", "172.22.1.22", ""));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
                if(response.isSuccessful()){
                    if(response.body() != null) {
                        String result = response.body().toString();
                        if (result.contains("disconnect")) {
                            isConnect = false;
                        }else {
                            isConnect = true;
                        }
                    }
                }else {
                    response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("debug", "570Ping defeat");
                isConnect = false;
            }
        });
        return isConnect;
    }

    public static List<DataItem> doQuery(String baseUri){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUri)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .build();

        Order order = new Order();
        order.setId(1);
        order.setMachineType(2);
        order.setOrderName("query 172.22.1.22 iso.3.6.1.4.1.6247.85");
        order.setOrderType(1);
        order.setIp("172.22.1.22");
        order.setMachine_port("iso.3.6.1.4.1.6247.85");

        OrderService orderService = retrofit.create(OrderService.class);
        Call<Result> call = orderService.reportOrder(order);


        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
                String extraString;
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        extraString = response.body().toString();
                        dataItems = dealData(extraString);
                    } else {
                        Log.d("debug", "Data repose: null");
                    }

                } else {
                    Log.d("debug", "Data repose: null" + response.errorBody().toString());

                }


            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("debug", "Data repose: null" + t.getStackTrace().toString());
            }
        });
        dataItems.add(new DataItem(1,"1","1", "1", "1", "1"));
        return dataItems;
    }

    private static List<DataItem> dealData(String extraString) {
        HashMap<Integer, String> patternMap = new HashMap<>();
        List<DataItem> dataItemList = new ArrayList<>();
        List<String> resultList = new ArrayList<>();
        HashMap<String, String> tvMap = new HashMap<>();
        String patternStr = null;
        int dataCount = 4;
        //send hz
        patternMap.put(1, "iso.3.6.1.4.1.6247.85.1.2.2.1.0\\s=\\s\\b\\w{6,9}\\b:\\s.+");
        //send bps
        patternMap.put(2, "iso.3.6.1.4.1.6247.85.1.2.2.2.0\\s=\\s\\b\\w{6,9}\\b:\\s.+");

        //receive hz
        patternMap.put(3, "iso.3.6.1.4.1.6247.85.1.2.3.1.0\\s=\\s\\b\\w{6,9}\\b:\\s.+");
        //receive bps
        patternMap.put(4, "iso.3.6.1.4.1.6247.85.1.2.3.2.0\\s=\\s\\b\\w{6,9}\\b:\\s.+");

        if(extraString != null || !extraString .equals(""))
        {
        for(int i = 0; i < dataCount; i++){
            patternStr = patternMap.get(i);
            Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(extraString);
                if(matcher.find()){
                    resultList.add(matcher.group());
                    }
                }
        }
        //
        patternMap.put(7, "(iso\\S+)(=\\s\\w{6,9})(\\w+\\n)");

        for(int k = 0; k < resultList.size(); k ++){
            patternStr = patternMap.get(7);
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(resultList.get(k));
            if(matcher.groupCount() > 0 && matcher.groupCount() >=4){
                String iso = matcher.group(1);
                String temp = matcher.group(2);
                int len = temp.length();
                String type = temp.substring(2, len -1);

                Log.d("debug", "type" + type);
                Log.d("debug", "temp" + temp);
                Log.d("debug", "iso" + iso);

                Log.d("debug", "matcher.group(3)" + matcher.group(3));

                tvMap.put(type, matcher.group(3));

                dataItemList.add(new DataItem(k, "order", type, tvMap.get(type), iso, "172.22.1.22"));
            }
        }

        return dataItemList;
        }
}
