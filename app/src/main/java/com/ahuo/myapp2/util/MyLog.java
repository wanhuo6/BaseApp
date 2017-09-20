package com.ahuo.myapp2.util;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by ahuo on 17-9-19.
 */

public class MyLog {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String NULL_TIPS = "Log with null object";
    public static final String PARAM = "Param";
    public static final String NULL = "null";
    public static String TAG_DEFAULT = "MyUserLog";
    public static final String SUFFIX = ".java";
    public static final int JSON_INDENT = 4;
    public static final int V = 1;
    public static final int D = 2;
    public static final int I = 3;
    public static final int W = 4;
    public static final int E = 5;
    public static final int A = 6;
    public static final int JSON = 7;
    public static final int XML = 8;
    private static boolean IS_SHOW_LOG = false;
    private static final int STACK_TRACE_INDEX = 5;

    public MyLog() {
    }

    public static void init(boolean isShowLog, String defaultTag) {
        IS_SHOW_LOG = isShowLog;
        TAG_DEFAULT = defaultTag;
    }

    public static void v(Object msg) {
        printLog(1, (String) null, new Object[]{msg});
    }

    public static void v(String tag, Object... objects) {
        printLog(1, tag, objects);
    }

    public static void d(Object msg) {
        printLog(2, (String) null, new Object[]{msg});
    }

    public static void d(String tag, Object... objects) {
        printLog(2, tag, objects);
    }

    public static void i(Object msg) {
        printLog(3, (String) null, new Object[]{msg});
    }

    public static void i(String tag, Object... objects) {
        printLog(3, tag, objects);
    }

    public static void w(Object msg) {
        printLog(4, (String) null, new Object[]{msg});
    }

    public static void w(String tag, Object... objects) {
        printLog(4, tag, objects);
    }

    public static void e(Object msg) {
        printLog(5, (String) null, new Object[]{msg});
    }

    public static void e(String tag, Object... objects) {
        printLog(5, tag, objects);
    }

    public static void a(Object msg) {
        printLog(6, (String) null, new Object[]{msg});
    }

    public static void a(String tag, Object... objects) {
        printLog(6, tag, objects);
    }

    public static void json(String jsonFormat) {
        printLog(7, (String) null, new Object[]{jsonFormat});
    }

    public static void json(String tag, String jsonFormat) {
        printLog(7, tag, new Object[]{jsonFormat});
    }

    public static void xml(String xml) {
        printLog(8, (String) null, new Object[]{xml});
    }

    public static void xml(String tag, String xml) {
        printLog(8, tag, new Object[]{xml});
    }

    private static void printLog(int type, String tagStr, Object... objects) {
        if (IS_SHOW_LOG) {
            String[] contents = wrapperContent(tagStr, objects);
            String tag = contents[0];
            String msg = contents[1];
            String headString = contents[2];
            switch (type) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    printDefault(type, tag, headString + msg);
                    break;
                case 7:
                    printJson(tag, msg, headString);
                    break;
                case 8:
                    printXml(tag, msg, headString);
            }

        }
    }

    private static String[] wrapperContent(String tagStr, Object... objects) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement targetElement = stackTrace[5];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + ".java";
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();
        if (lineNumber < 0) {
            lineNumber = 0;
        }

        String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        String tag = tagStr == null ? className : tagStr;
        String msg = objects == null ? "Log with null object" : getObjectsString(objects);
        String headString = "[ (" + className + ":" + lineNumber + ")#" + methodNameShort + " ] ";
        return new String[]{TextUtils.isEmpty(tag) ? TAG_DEFAULT : TAG_DEFAULT + tag, msg, headString};
    }

    private static String getObjectsString(Object... objects) {
        if (objects.length > 1) {
            StringBuilder var4 = new StringBuilder();
            var4.append("\n");

            for (int i = 0; i < objects.length; ++i) {
                Object object1 = objects[i];
                if (object1 == null) {
                    var4.append("Param").append("[").append(i).append("]").append(" = ").append("null").append("\n");
                } else {
                    var4.append("Param").append("[").append(i).append("]").append(" = ").append(object1.toString()).append("\n");
                }
            }

            return var4.toString();
        } else {
            Object object = objects[0];
            return object == null ? "null" : object.toString();
        }
    }

    public static void printJson(String tag, String msg, String headString) {
        String message;
        try {
            if (msg.startsWith("{")) {
                JSONObject lines = new JSONObject(msg);
                message = lines.toString(4);
            } else if (msg.startsWith("[")) {
                JSONArray var10 = new JSONArray(msg);
                message = var10.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException var9) {
            message = msg;
        }

        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] var11 = message.split(LINE_SEPARATOR);
        String[] var5 = var11;
        int var6 = var11.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            String line = var5[var7];
            Log.d(tag, "║ " + line);
        }

        printLine(tag, false);
    }

    public static void printXml(String tag, String xml, String headString) {
        if (xml != null) {
            xml = formatXML(xml);
            xml = headString + "\n" + xml;
        } else {
            xml = headString + "Log with null object";
        }

        printLine(tag, true);
        String[] lines = xml.split(LINE_SEPARATOR);
        String[] var4 = lines;
        int var5 = lines.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String line = var4[var6];
            if (!isEmpty(line)) {
                Log.d(tag, "║ " + line);
            }
        }

        printLine(tag, false);
    }

    public static String formatXML(String inputXML) {
        try {
            StreamSource e = new StreamSource(new StringReader(inputXML));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(e, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (Exception var4) {
            var4.printStackTrace();
            return inputXML;
        }
    }

    public static boolean isEmpty(String line) {
        return TextUtils.isEmpty(line) || line.equals("\n") || line.equals("\t") || TextUtils.isEmpty(line.trim());
    }

    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }

    }

    public static void printDefault(int type, String tag, String msg) {
        int index = 0;
        short maxLength = 3000;
        int countOfSub = msg.length() / maxLength;
        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; ++i) {
                String sub = msg.substring(index, index + maxLength);
                printSub(type, tag, sub);
                index += maxLength;
            }

            printSub(type, tag, msg.substring(index, msg.length()));
        } else {
            printSub(type, tag, msg);
        }

    }

    private static void printSub(int type, String tag, String sub) {
        switch (type) {
            case 1:
                Log.v(tag, sub);
                break;
            case 2:
                Log.d(tag, sub);
                break;
            case 3:
                Log.i(tag, sub);
                break;
            case 4:
                Log.w(tag, sub);
                break;
            case 5:
                Log.e(tag, sub);
                break;
            case 6:
                Log.wtf(tag, sub);
        }

    }

}
