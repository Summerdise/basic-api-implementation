package com.thoughtworks.rslist.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rs_event")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RsEventEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @NotEmpty
    private String eventName;
    @NotEmpty
    private String keyword;
    private int userId;


}
