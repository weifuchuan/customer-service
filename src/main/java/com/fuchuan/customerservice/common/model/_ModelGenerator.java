package com.fuchuan.customerservice.common.model;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.template.Engine;
import com.jfinal.template.Template;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static cn.hutool.core.util.BooleanUtil.isTrue;

public class _ModelGenerator {
  static String PAYLOAD_MODEL_DIR = "model";
  static String OUTPUT_DIR = "com/fuchuan/customerservice/common/model";

  public static void main(String[] args) {
    String payloadModelTmpl =
        FileUtil.readString(
            PathKit.getWebRootPath()
                + "/src/main/java/com/fuchuan/customerservice/common/model/_model.enjoy",
            "UTF-8");
    String payloadModelTSTmpl =
        FileUtil.readString(
            PathKit.getWebRootPath()
                + "/src/main/java/com/fuchuan/customerservice/common/model/_model-ts.enjoy",
            "UTF-8");

    Engine engine = new Engine();
    engine.setToClassPathSourceFactory();
    engine.addSharedStaticMethod(StrKit.class);

    Template payloadModelTemplate = engine.getTemplateByString(payloadModelTmpl);
    Template payloadModelTSTemplate = engine.getTemplateByString(payloadModelTSTmpl);

    List<Kv> models = new ArrayList<>();

    File[] files = FileUtil.ls(PAYLOAD_MODEL_DIR);
    for (File file : files) {
      if (file.getName().endsWith(".json")) {
        Kv modelKv = Kv.create();
        models.add(modelKv);

        String name =
            file.getName().substring(0, file.getName().lastIndexOf(".json")).replaceAll("-", "_");
        name = StrKit.toCamelCase(name);
        name = StrKit.firstCharToUpperCase(name);
        name += "Model";

        modelKv.set("name", "I" + name);

        String json = FileUtil.readString(file, "utf-8");
        JSONObject model = JSON.parseObject(json);

        Kv renderData = Kv.by("name", name);
        List<Kv> props = new ArrayList<>();
        List<Kv> propsTS = new ArrayList<>();
        renderData.set("props", props);
        modelKv.set("props", propsTS);

        model.forEach(
            (k, v) -> {
              try {
                Kv prop = Kv.create();
                Kv propTS = Kv.create();

                prop.set("name", k);
                propTS.set("name", k);

                JSONObject style = (JSONObject) v;

                prop.set(
                    "type",
                    style.getString("javaType") == null
                        ? convertType(style.getString("type"))
                        : style.getString("javaType"));
                propTS.set("type", convertTypeTS(style.getString("type")));

                if (isTrue(style.getBoolean("optional"))) {
                  prop.set("optional", true);
                  propTS.set("optional", true);
                }

                if (!StrKit.isBlank(style.getString("comment"))) {
                  prop.set("comment", style.getString("comment"));
                  propTS.set("comment", style.getString("comment"));
                }

                props.add(prop);
                propsTS.add(propTS);
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            });

        props.sort(Comparator.comparing(x -> x.getStr("name")));
        propsTS.sort(Comparator.comparing(x -> x.getStr("name")));

        String payloadModelRendered = payloadModelTemplate.renderToString(renderData);
        String payloadModelTSRendered =
            payloadModelTSTemplate.renderToString(Kv.by("models", models));

        FileUtil.writeString(
            payloadModelRendered,
            PathKit.getWebRootPath() + "/src/main/java/" + OUTPUT_DIR + "/" + name + ".java",
            "utf-8");

        FileUtil.writeString(
            payloadModelTSRendered,
            PathKit.getWebRootPath() + "/src/main/java/" + OUTPUT_DIR + "/ts/model.ts",
            "utf-8");
      }
    }
  }

  private static String convertType(String type) throws Exception {
    type = type.trim().toLowerCase();
    String arr = "";
    if (type.endsWith("[]")) {
      arr = "[]";
    }
    if (type.startsWith("str")) {
      return "String" + arr;
    }
    if (type.startsWith("int")) {
      return "Integer" + arr;
    }
    if (type.startsWith("long")) {
      return "Long" + arr;
    }
    if (type.startsWith("byte")) {
      return "Byte" + arr;
    }
    if (type.startsWith("char")) {
      return "Character" + arr;
    }
    if (type.startsWith("dou")) {
      return "Double" + arr;
    }
    if (type.startsWith("flo")) {
      return "Float" + arr;
    }
    if (type.startsWith("bo")) {
      return "Boolean" + arr;
    }
    if (type.startsWith("obj")) {
      return "Object" + arr;
    }
    return type;
  }

  private static String convertTypeTS(String type) throws Exception {
    type = type.trim().toLowerCase();
    String arr = "";
    if (type.endsWith("[]")) {
      arr = "[]";
    }
    if (type.startsWith("str") || type.startsWith("char")) {
      return "string" + arr;
    }
    if (type.startsWith("int")
        || type.startsWith("long")
        || type.startsWith("byte")
        || type.startsWith("dou")
        || type.startsWith("flo")) {
      return "number" + arr;
    }
    if (type.startsWith("bo")) {
      return "boolean" + arr;
    }
    if (type.startsWith("obj")) {
      return "any" + arr;
    }
    return "any";
  }
}
