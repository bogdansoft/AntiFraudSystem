package antifraud.model.request;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
public class AmountRequest {
    @Positive
    @NotNull
    Long amount;
}
