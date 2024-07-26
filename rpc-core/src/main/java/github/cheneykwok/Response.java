package github.cheneykwok;

import lombok.Data;

/**
 * @author gzc
 * @date 2024-07-26
 */
@Data
public class Response {

    private int code;

    private boolean success;

    private Object result;

    private String reason;
}
