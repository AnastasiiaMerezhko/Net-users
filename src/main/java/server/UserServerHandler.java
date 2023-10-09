package server;

import io.netty.channel.*;
import user.User;

public class UserServerHandler extends SimpleChannelInboundHandler<User> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, User user) {
        String name = user.getName();
        int age = user.getAge();
        System.out.println(name + " (Age: " + age + ") has connected.");
        ctx.writeAndFlush("Hello " + name);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}