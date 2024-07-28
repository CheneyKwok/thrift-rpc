namespace java github.cheneykwok.thrift.gen.task
namespace go rpc

struct StatusTaskRespon {
    1:required i32 code            //错误码
    2:required string message      //错误信息
    3:required bool isSuccess      //是否成功
    4:optional i32 traceId         //冗余字段
}

struct StatusTaskRequest {
    1:required string taskName           //任务名称（方法名称）
    2:required i64 srcInnerId            //任务目标表ID（订单ID，秒杀活动ID等）
    3:required string storeSysOutId      //系统外部ID
    4:required i64 taskRunDate           //任务执行时间(时间戳,单位毫秒)
    5:required i32 taskType              //任务类型 0订单状态任务 1营销活动状态任务 2商品状态任务
    6:required i32 afterStatus           //任务执行后状态
    7:required i32 beforeStatus          //任务执行前状态
    8:required bool isDuplicate          //任务是否需要去重
    9:optional map<string, string> header // 请求头
}

service TaskRpcService {
    // 定时修改状态任务
    StatusTaskRespon StatusTask(
        1:required StatusTaskRequest request
    )
}