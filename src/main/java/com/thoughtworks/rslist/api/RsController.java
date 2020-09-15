package com.thoughtworks.rslist.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
    private List<String> rsList = Arrays.asList("第一条事件", "第二条事件", "第三条事件");

    public List<String> getRsList() {
        return rsList;
    }

    @GetMapping("/rs/{index}")
    public String getOneRsItemFromList(@PathVariable int index) {
        return getRsList().get(index);
    }

    @GetMapping("/rs/list")
    public String getRsItemsFromListByStartNumber(@RequestParam int start, @RequestParam int end) {
        return getRsList().subList(start, end).toString();
    }

}
