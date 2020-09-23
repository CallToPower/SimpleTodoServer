package de.calltopower.simpletodo.impl.requestbody;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import de.calltopower.simpletodo.api.requestbody.STDRequestBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Request body for a signup
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class STDSignupRequestBody implements STDRequestBody {

    @NotBlank
    @Size(min = 3, max = 100)
    public String username;

    @NotBlank
    @Size(max = 100)
    @Email
    public String email;

    @NotBlank
    @Size(min = 6, max = 100)
    public String password;

    public String jsonData;

    @Override
    public String toString() {
        return String.format("STDSignupRequestBody[username=%s, email=%s]", username, email);
    }

}
