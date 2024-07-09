package com.example.getway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;

import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.nacos.common.http.param.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.util.Collections;
import java.util.List;


@Configuration
public class GatewayConfiguration {
    private final ServerProperties serverProperties;
    private final ApplicationContext applicationContext;
    private final ResourceProperties resourceProperties;
    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;
    public GatewayConfiguration(ServerProperties serverProperties,
                                ApplicationContext applicationContext,
                                ResourceProperties resourceProperties,
                                ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.serverProperties = serverProperties;
        this.applicationContext = applicationContext;
        this.resourceProperties = resourceProperties;
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }



    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @Bean(name = "myBlockRequestHandler")
    public BlockRequestHandler myBlockRequestHandler() {
        BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
            @SneakyThrows
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {

                Result result;
                if (throwable instanceof FlowException) {
                    result = Result.builder().code(100).msg("接口限流了").build();

                } else if (throwable instanceof DegradeException) {
                    result = Result.builder().code(101).msg("服务降级了").build();

                } else if (throwable instanceof ParamFlowException) {
                    result = Result.builder().code(102).msg("热点参数限流了").build();

                } else if (throwable instanceof SystemBlockException) {
                    result = Result.builder().code(103).msg("触发系统保护规则").build();

                } else if (throwable instanceof AuthorityException) {
                    result = Result.builder().code(104).msg("授权规则不通过").build();
                } else {
                    result = Result.builder().code(105).msg("sentinel 未知异常").build();
                }

                return ServerResponse.status(HttpStatus.BAD_GATEWAY)
                        .contentType(org.springframework.http.MediaType.valueOf(MediaType.APPLICATION_JSON))
                        .body(BodyInserters.fromValue(new ObjectMapper().writeValueAsString(result)));
            }
        };
        return blockRequestHandler;
    }

    @Bean("myErrorWebExceptionHandler")
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler myErrorWebExceptionHandler(ErrorAttributes errorAttributes) {

        MyErrorWebExceptionHandler exceptionHandler = new MyErrorWebExceptionHandler(
                errorAttributes,
                this.resourceProperties,
                this.serverProperties.getError(),
                this.applicationContext);

        exceptionHandler.setViewResolvers(this.viewResolvers);
        exceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
        exceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
        return exceptionHandler;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler(BlockRequestHandler myBlockRequestHandler) {

        //重定向bloack处理
        //GatewayCallbackManager.setBlockHandler(new RedirectBlockRequestHandler("https://www.extlight.com"));

        //自定义bloack处理
        GatewayCallbackManager.setBlockHandler(myBlockRequestHandler);
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

}
