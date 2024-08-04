package github.cheneykwok;

import github.cheneykwok.server.properties.TProtocolType;
import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.TTransport;

public class ThriftTProtocolFactory {

    public static TProtocolFactory getProtocolFactory(TProtocolType protocolType) {
        switch (protocolType) {
            case TBinaryProtocol -> {
                return new TBinaryProtocol.Factory();
            }
            case TJSONProtocol -> {
                return new TJSONProtocol.Factory();
            }
            case TSimpleJSONProtocol -> {
                return new TSimpleJSONProtocol.Factory();
            }
            case TTupleProtocol -> {
                return new TTupleProtocol.Factory();
            }
            default -> {
                return new TCompactProtocol.Factory();
            }
        }
    }

    public static TProtocol getProtocol(TProtocolType protocolType, TTransport tTransport) {
        return getProtocolFactory(protocolType).getProtocol(tTransport);
    }
}
