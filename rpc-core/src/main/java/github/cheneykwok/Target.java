package github.cheneykwok;

import org.springframework.util.Assert;

public class Target<T> {
    private final Class<T> type;

    public Target(Class<T> type) {
        // 校验 type 参数不能为空
        Assert.notNull(type, "type must not be null");
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }
}
