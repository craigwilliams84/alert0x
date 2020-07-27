package eth.craig.alert0x.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage {

    private String to;

    private String from;

    private String contentText;

    private String subject;
}
