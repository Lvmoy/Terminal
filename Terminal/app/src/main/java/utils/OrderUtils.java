package utils;

import android.util.Log;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interfaces.OrderService;
import okhttp3.OkHttpClient;
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
    private static List<DataItem> dataList = new ArrayList<>();

    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(60, TimeUnit.SECONDS).
            readTimeout(60, TimeUnit.SECONDS).
            writeTimeout(60, TimeUnit.SECONDS).build();

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

    public static String doPingForInfo(String ip){
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        StringBuilder result = null;
        String resultStr = null;
        String cmdString = "/system/bin/ping -c " + 4 + " " + ip;
        try {
            process = runtime.exec(cmdString);
            result = new StringBuilder();
            BufferedInputStream inputStream = new BufferedInputStream(process.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null){
                result.append(line + "\r\n");
            }
            resultStr = result.toString();

            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            inputStream.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = process.exitValue();  //接收执行完毕的返回值ss
        if (i == 0) {
            Log.d("debug", "执行完成.");
        } else {
            Log.d("debug", "执行失败.");
        }

        process.destroy();
        process = null;

        return resultStr;
    }
    public static boolean do570Ping(String ipStr){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(LoganSquareConverterFactory.create())
                .baseUrl(ipStr)
                .client(client)
                .build();

        OrderService orderService =  retrofit.create(OrderService.class);
        Call<Result> call = orderService.reportOrder(new Order(2, 2, 5, "570 PING", "172.22.1.22", "", "" ,""));
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
        if(call.isExecuted() || call.isCanceled()){
            return isConnect;
        }else {
            do570Ping(ipStr);
            return isConnect;
        }

    }

    public static boolean doAllQuery(String baseUri, final List<DataItem> dataItems) {
                return doQueueQuery(baseUri,dataItems);
    }

    private static boolean doQueueQuery(String baseUri, List<DataItem> dataItems) {
        boolean flag = false;
        Order[] orders = new Order[4];
        //sendHz
        orders[0] = new Order();
        orders[0].setId(0);
        orders[0].setIp("172.22.1.22");
        orders[0].setMachine_port("iso.3.6.1.4.1.6247.85.1.2.2.1.0");
        orders[0].setMachineType(2);
        orders[0].setOrderName("request_sendHz");
        orders[0].setOrderType(2);
        orders[0].setType("");
        orders[0].setValue("");
        //sendBps
        orders[1] = new Order();
        orders[1].setId(0);
        orders[1].setIp("172.22.1.22");
        orders[1].setMachine_port("iso.3.6.1.4.1.6247.85.1.2.2.2.0");
        orders[1].setMachineType(2);
        orders[1].setOrderName("request_sendBps");
        orders[1].setOrderType(2);
        orders[1].setType("");
        orders[1].setValue("");
        //receiveHz
        orders[2] = new Order();
        orders[2].setId(0);
        orders[2].setIp("172.22.1.22");
        orders[2].setMachine_port("iso.3.6.1.4.1.6247.85.1.2.3.1.0");
        orders[2].setMachineType(2);
        orders[2].setOrderName("request_receiveHz");
        orders[2].setOrderType(2);
        orders[2].setType("");
        orders[2].setValue("");
        //receiveBps
        orders[3] = new Order();
        orders[3].setId(0);
        orders[3].setIp("172.22.1.22");
        orders[3].setMachine_port("iso.3.6.1.4.1.6247.85.1.2.3.2.0");
        orders[3].setMachineType(2);
        orders[3].setOrderName("request_receiveBps");
        orders[3].setOrderType(2);
        orders[3].setType("");
        orders[3].setValue("");

        for(int i = 0; i < orders.length; i ++){
            flag = doSingleQuery(i, baseUri, orders[i], dataItems);
        }
        return flag;
    }

    private static boolean doSingleQuery(final int i, String baseUri, Order order, final List<DataItem> dataItems) {
        boolean flag = false;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUri)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .client(client)
                .build();

        OrderService orderService = retrofit.create(OrderService.class);
        Call<Result> call = orderService.reportOrder(order);
        try {
            retrofit2.Response<Result> response = call.execute();
            String extraString;
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    extraString = response.body().toString();
                    if(extraString != null || !extraString.equals("")){
                        if(!dealSingleData(i ,extraString, dataItems)){
                        }else {
                            flag = true;
                        }
                    }
                } else {
                    extraString = "repose.body() == null";
                }

            } else {
                extraString = response.errorBody().toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void doQueueEdit(String baseUri, List<DataItem> dataList){
        if(dataList != null && dataList.size() > 0){

            List<Order> orders = new ArrayList<>();
            for(int i = 0; i < dataList.size(); i++){
                orders.add(i, new Order(dataList.get(i).getId(), 2, 4, dataList.get(i).getName(), dataList.get(i).getIp(),
                        dataList.get(i).getIso(), dataList.get(i).getType(), dataList.get(i).getValue()));

            }

            for (int j = 0; j < orders.size(); j ++){
                doSingleEdit(baseUri, orders.get(j));
            }
        }
    }

    private static void doSingleEdit(String baseUri, Order order) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUri)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .client(client)
                .build();

        OrderService orderService = retrofit.create(OrderService.class);
        Call<Result> call = orderService.reportOrder(order);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private static boolean dealSingleData(int i, String extraString, List<DataItem> dataItems) {
        HashMap<String, String> tvMap = new HashMap<>();
        String patternStr = "(iso\\S+)(=\\s\\w{6,9})(:\\s.*\\d{6,9})";
        String[] paS = new String[]{"iso\\S+", "=\\s\\w{6,9}", ":\\s.*\\d{6,9}"};

        String iso = null;
        String temp = null;
        String type = null;
        String value = null;

        for (int j = 0; j < paS.length; j++) {
            Pattern pattern = Pattern.compile(paS[j]);
            Matcher matcher = pattern.matcher(extraString);
            for (int h = 0; h < paS.length; h++) {
                if (matcher.find()) {
                    switch (j) {
                        case 0:
                            iso = matcher.group();
                            break;
                        case 1:
                            temp = matcher.group();
                            int len = temp.length();
                            type = temp.substring(2, len);
                            break;
                        case 2:
                            temp = matcher.group();
                            int length = temp.length();
                            value = temp.substring(2, length);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        dataItems.add(i, new DataItem(i, "order", type, value, iso, "172.22.1.22"));
        if(i == 3){
            return true;
        }else {
            return false;
        }
        }

    private static void dealData(String extraString, List<DataItem> itemList) {
        HashMap<Integer, String> patternMap = new HashMap<>();
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

        if(extraString != null && !extraString .equals(""))
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

                itemList.add(new DataItem(k, "order", type, tvMap.get(type), iso, "172.22.1.22"));
            }
        }

        }
}
