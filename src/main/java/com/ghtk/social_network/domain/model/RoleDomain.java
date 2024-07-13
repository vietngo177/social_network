package com.ghtk.social_network.domain.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class RoleDomain {
    Long roleId;
    String roleName;
}
