namespace java github.cheneykwok.thrift.gen.inner

// 响应报文
struct Response {
    1:required i32 code           // 错误码 成功:200
    2:required string message     // 错误/成功信息
    3:required bool isSuccess     // 是否成功
    4:optional string data        // 响应数据
}
// 请求报文
struct Request {
    1:required string classCanonicalName // 类的全限定名
    2:required string methodName // 方法名
    3:optional list<string> parameters // 方法参数
    4:optional list<string> parameterTypes // 方法参数类型
    5:optional map<string, string> header // 请求头
}

//定义服务
service InnerRpcService {

    // 统一入口
    Response request(1:required Request request)

}