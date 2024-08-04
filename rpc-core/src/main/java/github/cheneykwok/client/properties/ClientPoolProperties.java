package github.cheneykwok.client.properties;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("rpc.client.pool")
public class ClientPoolProperties {

    /**
     * 最大等待时间
     */
    private long maxWaitTimeMills = 100;

    /**
     * 池管理的对象实例总数上限。负值表示没有限制
     */
    private int maxTotal = GenericKeyedObjectPoolConfig.DEFAULT_MAX_TOTAL;

    /**
     * 设置池为每个键分配的对象实例数（已检出或空闲）的限制。负值表示没有限制。
     */
    private int maxTotalPerKey = GenericKeyedObjectPoolConfig.DEFAULT_MAX_TOTAL_PER_KEY;

    /**
     * 设置池中每个键的空闲实例数上限
     * 如果在负载过重的系统中将 maxIdlePerKey 设置得太低，则可能会看到对象被销毁，而新对象几乎立即被创建。
     * 这是因为活动线程暂时返回对象的速度比请求对象的速度快，导致空闲对象的数量超过 maxIdlePerKey。负载过重的系统中 maxIdlePerKey 的最佳值会有所不同，但默认值是一个很好的起点。
     */
    private int maxIdlePerKey = GenericKeyedObjectPoolConfig.DEFAULT_MAX_IDLE_PER_KEY;

    /**
     * 设置每个键控子池中要维护的最小空闲对象数的目标
     * 此设置仅在其为正数且getDurationBetweenEvictionRuns()大于零时才有效。如果是这种情况，则会尝试确保每个子池在空闲对象驱逐运行期间具有所需的最小实例数。
     */
    private int minIdlePerKey = GenericKeyedObjectPoolConfig.DEFAULT_MIN_IDLE_PER_KEY;

    /**
     * 池对象创建时时验证是否正常可用
     */
    private boolean testOnCreate = true;

    /**
     * 池对象借出时验证是否正常可用
     */
    private boolean testOnBorrow = true;

    /**
     * 池对象归还时验证是否正常可用
     */
    private boolean testOnReturn = true;
}
