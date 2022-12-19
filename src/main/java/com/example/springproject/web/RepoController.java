package com.example.springproject.web;


import com.example.springproject.domain.Repo;
import com.example.springproject.service.RepoService;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/repo/")
public class RepoController {

    /**
     * 读取json文件，返回json串
     * @param fileName file name
     * @return //
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(Files.newInputStream(jsonFile.toPath()), StandardCharsets.UTF_8);
            int ch = 0;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }

            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Autowired
    private RepoService repoService;

//    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "getInfo", method= RequestMethod.GET)
    public Optional<Repo> getInfo_1(String repo){
        return repoService.FindByRepoName(repo);
    }

//    @GetMapping("getInfo2")
//    public ArrayList<String> getInfo_2() {
//
//        ArrayList<String> arrayList = new ArrayList<>();
//
//        String json = "";
//
//        try {
//            BufferedReader in = new BufferedReader(new FileReader("src/temp.json"));
//            String str;
//            while ((str = in.readLine()) != null) {
////                System.out.println(str);
//                json = json.concat(str);
//            }
////            System.out.println(str);
//        } catch (IOException e) {
//            System.err.println(e);
//        }
//
//        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
////        System.out.println(json);
//        arrayList.add(""+JsonPath.read(document, "$.developers"));
//        arrayList.add(""+JsonPath.read(document, "$.issue"));
//        arrayList.add(""+JsonPath.read(document, "$.developers"));
//        arrayList.add(""+JsonPath.read(document, "$.developers"));
//
//
//        return arrayList;
//    }

    @RequestMapping(value = "test", method= RequestMethod.GET)
    public Optional<Repo> Id_test(int id){
        return repoService.FindById(id);
    }

}
