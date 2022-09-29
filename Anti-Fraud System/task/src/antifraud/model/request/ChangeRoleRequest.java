package antifraud.model.request;

import antifraud.model.Role;
import antifraud.validation.RoleSubSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChangeRoleRequest {
    @NotBlank
    String username;
    @NonNull
    @RoleSubSet(anyOf = {Role.MERCHANT, Role.SUPPORT})
    Role role;
}
