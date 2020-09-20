package com.thoughtworks.rslist.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String userName;
    private String gender;
    private int age;
    private String email;
    private String phone;
    private int voteNum;

    @OneToMany(mappedBy = "userId",cascade = CascadeType.REMOVE)
    private List<RsEventEntity> rsEvent;
}
