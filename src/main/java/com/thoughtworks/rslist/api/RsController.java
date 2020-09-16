package com.thoughtworks.rslist.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.RsItem;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {


    private List<RsItem> rsList = getRsList();

    public List<RsItem> getRsList() {
        List<RsItem> rsList = new ArrayList<RsItem>();
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
    public void insertRsItem(@RequestBody RsItem rsItem) throws JsonProcessingException {
        rsList.add(rsItem);
    }

    @PostMapping("/rs/fix/{index}")
    public void fixRsItem(@PathVariable int index, @RequestBody String rsItemString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RsItem rsItem = objectMapper.readValue(rsItemString, RsItem.class);
        if (rsItem.getName() == null && rsItem.getKeyword() == null) {
            return;
        }
        if (rsItem.getName() == null) {
            rsList.get(index).setKeyword(rsItem.getKeyword());
        } else if (rsItem.getKeyword() == null) {
            rsList.get(index).setName(rsItem.getName());
        } else {
            rsList.get(index).setName(rsItem.getName());
            rsList.get(index).setKeyword(rsItem.getKeyword());
        }
    }

    @GetMapping("/rs/delete/{index}")
    public void deleteRsItem(@PathVariable int index) {
        if (index >= rsList.size()) {
            return;
        }
        rsList.remove(index);
    }
    
}
