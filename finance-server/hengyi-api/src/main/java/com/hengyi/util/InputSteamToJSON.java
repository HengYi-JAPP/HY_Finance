package com.hengyi.util;

/**
 * Created by XuZhuang on 2017/10/25.
 * 解决跨域传参数，
 * 取request流里面的参数 转成JSON 或者bean对象
 */
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.util.List;

/***
 * 数据传输对象，将前台传入的流统一转化为fastJson对象
 */
public class InputSteamToJSON {
    /**
     * 把流读取出来转换成JSON
     *
     * @param in 输入流
     * @return net.sf.json.JSONObject
     */
    public static JSONObject getJSON(InputStream in) {
        JSONObject json = null;
        ByteArrayOutputStream baos = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in));
            String str = "";
            baos = new ByteArrayOutputStream();
            while ((str = br.readLine()) != null) {
                baos.write(str.getBytes());
            }
            baos.flush();
            LoggerUtil.info("传入>>>>>>>"+baos.toString());
//            System.out.println("传入>>>>>>>>>>>>>:"+baos.toString());
            json = JSONObject.parseObject(baos.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (br != null) {
                    br.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return json;
    }

    /***
     * 把流读出来转化成Json，用于定时任务的获取，防止其不断打印
     * @param in
     * @return
     */

    /***
     * request 流对象 转换成 bean对象
     * @param in
     * @param clzss
     * @param <T>
     * @return
     */
    public static <T> T getParams(InputStream in,Class<T> clzss){
        JSONObject json = getJSON(in);
        return JSONObject.toJavaObject(json,clzss);
    }

    /**
     * @param in 信息输出流
     * @return JSONArray
     * @throws
     */
    public static JSONArray getJSONArray(InputStream in) {

        JSONArray jsonarray = null;
        ByteArrayOutputStream baos = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in));
            String str = "";
            baos = new ByteArrayOutputStream();
            while ((str = br.readLine()) != null) {
                baos.write(str.getBytes());
            }
            baos.flush();
            LoggerUtil.info("传入Array>>>>>>>>"+baos.toString());
//            System.out.println("传入Array>>>>>>>>>>>>>:"+baos.toString());
            jsonarray = JSONArray.parseArray(baos.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (br != null) {
                    br.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return jsonarray;

    }
    public static  <T> List<T> getJSONList(InputStream in, Class<T> clazz) {

        List<T> list=null;
        ByteArrayOutputStream baos = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in));
            String str = "";
            baos = new ByteArrayOutputStream();
            while ((str = br.readLine()) != null) {
                baos.write(str.getBytes());
            }
            baos.flush();
            LoggerUtil.info("传入Array>>>>>>>>"+baos.toString());
//            System.out.println("传入Array>>>>>>>>>>>>>:"+baos.toString());
            list = JSONArray.parseArray(baos.toString(),clazz);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (br != null) {
                    br.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return list;

    }

    /**
     *
     * @Title: getFastJSON
     * @Description: 流中的信息转成JSON
     * @param is 信息流
     * @throws IOException
     * @return JSON
     */
    public static JSONObject getFastJSON(InputStream is) throws IOException {
        JSONObject json = null;
        BufferedReader br = null;
        ByteArrayOutputStream baos = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            baos = new ByteArrayOutputStream();
            String str = "";
            while ((str = br.readLine()) != null) {
                baos.write(str.getBytes());
            }
            baos.flush();
            LoggerUtil.info("fast传入>>>>>>>>>"+ baos.toString());
//            System.out.println("fast传入>>>>>>>>>>>>>:"+baos.toString());
            json = JSONObject.parseObject(baos.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    /**
     *
     * @Title: getFastJSON
     * @Description: 流中的信息转成JSON
     * @param is 信息流
     * @param charSet 字符集
     * @throws IOException
     * @return JSON
     */
    public static JSONObject getFastJSON(InputStream is, String charSet) throws IOException {
        JSONObject json = null;
        BufferedReader br = null;
        ByteArrayOutputStream baos = null;
        try {
            br = new BufferedReader(new InputStreamReader(is, charSet));
            baos = new ByteArrayOutputStream();
            String str = "";
            while ((str = br.readLine()) != null) {
                baos.write(str.getBytes());
            }
            baos.flush();
            LoggerUtil.info("fast传入>>>>>>>:"+ baos.toString());
//            System.out.println("fast传入>>>>>>>>>>>>>:"+baos.toString());
            json = JSONObject.parseObject(baos.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    /**
     *
     * @Title: getFastJSONArray
     * @Description: 流中的信息转成JSONArray
     * @param is 信息流
     * @throws IOException
     * @return JSONArray
     */
    public static JSONArray getFastJSONArray(InputStream is) throws IOException {
        JSONArray jsonArray = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String str = "";
        while ((str = br.readLine()) != null) {
            baos.write(str.getBytes());
        }
        baos.flush();
        LoggerUtil.info("fastJsonArr传入>>>>>>>>>");
//        System.out.println("fastJsonArr传入>>>>>>>>>>>>>:"+baos.toString());
        jsonArray = JSONArray.parseArray(baos.toString());
        try {
            if (br != null) {
                br.close();
            }
            if (baos != null) {
                baos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    /**
     *
     * @Title: getFastJSONArray
     * @Description: 流中的信息转成JSONArray
     * @param is 信息流
     * @param charSet 字符集
     * @throws IOException
     * @return JSONArray
     */
    public static JSONArray getFastJSONArray(InputStream is, String charSet) throws IOException {
        JSONArray jsonArray = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(is, charSet));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String str = "";
        while ((str = br.readLine()) != null) {
            baos.write(str.getBytes());
        }
        baos.flush();
        LoggerUtil.info("fastJsonArr传入>>>>>>>>>:"+baos.toString());
//        System.out.println("fastJsonArr传入>>>>>>>>>>>>>:"+baos.toString());
        jsonArray = JSONArray.parseArray(baos.toString());
        try {
            if (br != null) {
                br.close();
            }
            if (baos != null) {
                baos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}