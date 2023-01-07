package com.example.mogakko.domain.group.dto;

import com.example.mogakko.domain.group.enums.GroupStatus;
import com.example.mogakko.domain.post.enums.Term;
import com.example.mogakko.domain.post.enums.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyGroupDTO {

    private Long groupId;

    private GroupStatus groupStatus;

    private Type type;

    private String title;

    private String groupMaster;

    private Term term;

}
