package com.thoughtworks.rslist.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.Entity.RsEventEntity;
import com.thoughtworks.rslist.Repository.RsEventRepository;
import com.thoughtworks.rslist.Repository.UserRepository;
import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.RsItem;
import com.thoughtworks.rslist.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;

    private List<RsItem> rsList = getRsList();

    public List<RsItem> getRsList() {
        List<RsItem> rsList = new ArrayList<RsItem>();
        UserDto userDto = new UserDto("xiaowang",19,"female","a@thoughtworks.com","18888888888");
        rsList.add(new RsItem("第一条事件", "1",userDto));
        rsList.add(new RsItem("第二条事件", "2",userDto));
        rsList.add(new RsItem("第三条事件", "3",userDto));
        return rsList;
    }

    @GetMapping("/rs/all")
    public ResponseEntity<String> getAllRsItems() {
        return ResponseEntity.ok(rsList.toString());
    }


    @GetMapping("/rs/{index}")
    public ResponseEntity<String> getOneRsItemFromList(@PathVariable int index) {
        return ResponseEntity.ok(rsList.get(index).toString());
    }

    @GetMapping("/rs/list")
    public ResponseEntity<String> getRsItemsFromListByStartNumber(@RequestParam int start, @RequestParam int end) {
        return ResponseEntity.ok(rsList.subList(start, end).toString());
    }

    @PostMapping("/rs/all")
    public ResponseEntity insertRsItem(@Valid @RequestBody RsItem rsItem) throws JsonProcessingException {
        if(rsItem.getUserDto().getUserName().equals("xiaowang")){
            rsList.add(rsItem);
            return ResponseEntity.created(null).build();
        }else{
            UserController userController = new UserController();
            userController.add(rsItem.getUserDto());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/rs/fix/{index}")
    public ResponseEntity fixRsItem(@PathVariable int index, @RequestBody String rsItemString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RsItem rsItem = objectMapper.readValue(rsItemString, RsItem.class);
        if (rsItem.getName() == null && rsItem.getKeyword() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (rsItem.getName() == null) {
            rsList.get(index).setKeyword(rsItem.getKeyword());
        } else if (rsItem.getKeyword() == null) {
            rsList.get(index).setName(rsItem.getName());
        } else {
            rsList.get(index).setName(rsItem.getName());
            rsList.get(index).setKeyword(rsItem.getKeyword());
        }
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/rs/delete/{index}")
    public void deleteRsItem(@PathVariable int index) {
        if (index >= rsList.size()) {
            return;
        }
        rsList.remove(index);
    }

    @PostMapping("/rs/event")
    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent){
        if(!userRepository.existsById(rsEvent.getUserId())){
            return ResponseEntity.badRequest().build();
        }
        RsEventEntity entity = RsEventEntity.builder()
                .eventName(rsEvent.getEventName())
                .keyword(rsEvent.getKeyword())
                .userId(rsEvent.getUserId())
                .build();

        rsEventRepository.save(entity);
        return ResponseEntity.created(null).build();
    }
}
