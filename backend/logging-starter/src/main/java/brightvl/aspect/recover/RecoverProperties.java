package brightvl.aspect.recover;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties("application.recover")
public class RecoverProperties {

    private boolean enabled = true;

    private List<String> noRecoverFor = new ArrayList<>();
}
