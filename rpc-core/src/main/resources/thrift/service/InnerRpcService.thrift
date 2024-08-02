namespace java github.cheneykwok.thrift.gen.inner

// 响应报文
struct InnerResponse {
    1:required i32 code           // 错误码 成功:200
    2:required string message     // 错误/成功信息
    3:required bool isSuccess     // 是否成功
    4:optional string data        // 响应数据
}
// 请求报文
struct InnerRequest {
    1:required string path // 路径
    2:required string arg // 方法参数
}

//定义服务
service InnerRpcService {

    // 统一入口
    InnerResponse request(1:required InnerRequest request)

}