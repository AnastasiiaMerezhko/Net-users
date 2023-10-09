package user;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public final class UserClient {

    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 8000;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new UserClientHandler());
                        }
                    });

            ChannelFuture f = b.connect(SERVER_HOST, SERVER_PORT).sync();
            System.out.println("Connected to server");

            User user = new User("Alice", 25);
            f.channel().writeAndFlush(user);

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}