package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {

    private List<RsItem> rsList = Arrays.asList(
            new RsItem("第一条事件", "1"),
            new RsItem("第二条事件", "2"),
            new RsItem("第三条事件", "3"));


    @GetMapping("/rs/{index}")
    public String getOneRsItemFromList(@PathVariable int index) {
        return rsList.get(index).toString();
    }

    @GetMapping("/rs/list")
    public String getRsItemsFromListByStartNumber(@RequestParam int start, @RequestParam int end) {
        return rsList.subList(start, end).toString();
    }

}
