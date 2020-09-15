package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.RsItem;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {

    private List<RsItem> rsList = getRsList();

    public List<RsItem> getRsList() {
        List<RsItem> rsList =new ArrayList<RsItem>();
        rsList.add(new RsItem("第一条事件", "1"));
        rsList.add(new RsItem("第二条事件", "2"));
        rsList.add(new RsItem("第三条事件", "3"));
        return rsList;
    }

    @GetMapping("/rs/all")
    public String getAllRsItems() {
        return rsList.toString();
    }

    @GetMapping("/rs/{index}")
    public String getOneRsItemFromList(@PathVariable int index) {
        return rsList.get(index).toString();
    }

    @GetMapping("/rs/list")
    public String getRsItemsFromListByStartNumber(@RequestParam int start, @RequestParam int end) {
        return rsList.subList(start, end).toString();
    }

    @PostMapping("/rs/all")
    public void insertRsItem(@RequestBody String rsItemString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RsItem rsItem = objectMapper.readValue(rsItemString,RsItem.class);
        RsItem newRs =new RsItem(rsItem.getName(),rsItem.getKeyword());
        rsList.add(newRs);
    }
}
