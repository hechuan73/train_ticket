package auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * user login dto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicAuthDto implements Serializable {
    private static final long serialVersionUID = 5505144168320447022L;

    private String username;
    private String password;
}
