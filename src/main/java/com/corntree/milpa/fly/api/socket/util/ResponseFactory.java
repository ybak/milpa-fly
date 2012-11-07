package com.corntree.milpa.fly.api.socket.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.corntree.milpa.fly.protocol.Commons.PayloadType;
import com.corntree.milpa.fly.protocol.ServerPacket.ResponseCode;
import com.corntree.milpa.fly.protocol.ServerPacket.ServerResponse;
import com.corntree.milpa.fly.protocol.ServerPacket.ServerResponse.Builder;
import com.google.protobuf.ByteString;

public class ResponseFactory {

    public static ServerResponse buildServerResponse(ResponseCode responseCode, String desc, PayloadType payloadType,
            ByteString payloadData) {
        Assert.isTrue(!((payloadType == null) ^ (payloadData == null)), "payloadType and payloadData must be paired.");
        Builder builder = ServerResponse.newBuilder().setCode(responseCode);
        if (StringUtils.isNotBlank(desc)) {
            builder.setDesc(desc);
        }
        if (payloadType != null) {
            builder.setPayloadType(payloadType);
        }
        if (payloadData != null) {
            builder.setPayloadData(payloadData);
        }
        return builder.build();
    }

    public static ServerResponse buildServerResponse(ResponseCode responseCode) {
        return buildServerResponse(responseCode, null, null, null);
    }
    
    public static ServerResponse buildServerResponse(ResponseCode responseCode, String desc) {
        return buildServerResponse(responseCode, desc, null, null);
    }

    public static ServerResponse buildServerResponse(ResponseCode responseCode, PayloadType payloadType,
            ByteString payloadData) {
        return buildServerResponse(responseCode, null, payloadType, payloadData);
    }

    public static ServerResponse buildServerResponse(PayloadType payloadType, ByteString payloadData) {
        return buildServerResponse(ResponseCode.OK, null, payloadType, payloadData);
    }

}
