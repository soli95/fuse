package ServerThread;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * @author 김솔이
 * 2016.11.20 
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 */

public final class ServerThread extends Thread{//논블로킹 IO모드: 요청작업의 결과를 성공여부와 관련 없이 돌려줌
    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    SslContext sslCtx = null;
    public ServerThread(){
    	
    }
    public void run() {
        // Configure SSL.
        
        if (SSL) {
            SelfSignedCertificate ssc = null;
			try {
				ssc = new SelfSignedCertificate();
			} catch (CertificateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
				sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
			} catch (SSLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
            sslCtx = null;
        }

        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);//스레드 그룹. 최대 스레드 수=1(단일 스레드)
        EventLoopGroup workerGroup = new NioEventLoopGroup();//스레드 그룹. 스레드 수 없음: HW 코어 수를 기준으로 결정
        try {
        	//부트스트랩 객체 생성. 부트스트랩: 네트워크 애플리케이션 설정에 필요한 내용을 담고 있음(포트,소켓모드/옵션,스레트,프로토콜)
            ServerBootstrap b = new ServerBootstrap();
            //아래 한문장으로 여러 변수로 초기화를 할수 있음 (builder패턴)
            b.group(bossGroup, workerGroup)//클라이언트 연결 수락 부모스레드, 연결된 클라이언트 소켓으로부터 데이터 입출력 및 이벤트 처리 담당
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 100)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {//클라이언트로부 연결된 채널이 초기화 될 때의 기본 동작, 서버 소켓채널과 채널 파이프라인 연결
                     ChannelPipeline p = ch.pipeline();//채널 파이프라인 객체 생성
                     if (sslCtx != null) {
                         p.addLast(sslCtx.newHandler(ch.alloc()));
                     }
                     //p.addLast(new LoggingHandler(LogLevel.INFO));
                     p.addLast(new ServerHandler());//접속된 클라이언트로부터 받은 데이터를 처리할 핸들러 지정
                 }
             });

            // Start the server.
            ChannelFuture f = b.bind(PORT).sync();//접속 포트 지정
            
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
