package com.example.securitytesttoken.aop;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


@Aspect
@Component
@Slf4j
public class ThreadWaitingAspect {
	
	private final Map<String, Integer> waitingThreadCountMap = new ConcurrentHashMap<>();
	private final ObjectMapper mapper = new ObjectMapper();
	private final File mapFile = new File("thread_waiting_count.json");
	
	public ThreadWaitingAspect() {
        if (mapFile.exists()) {
            restoreMapFromFile();
        }
    }
	
	@Pointcut("execution(* com.example.securitytesttoken.serviceImpl..*(..)) || " +
	          "execution(* com.example.securitytesttoken.provider..*(..)) || " +
	          "execution(* com.example.securitytesttoken.Listener..*(..)) || " +
	          "execution(* com.example.securitytesttoken.handler..*(..))")
	public void pointCut() {
	}
	
    @Before("pointCut()")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        incrementWaitingThreadCount(methodName);
    }

    @AfterReturning("pointCut()")
    public void afterMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        decrementWaitingThreadCount(methodName);
    }

    private void incrementWaitingThreadCount(String methodName) {
    	waitingThreadCountMap.putIfAbsent(methodName, 0);
        waitingThreadCountMap.put(methodName, waitingThreadCountMap.get(methodName) + 1);
        saveMapToFile();
    }

    private void decrementWaitingThreadCount(String methodName) {
        waitingThreadCountMap.put(methodName, waitingThreadCountMap.get(methodName) - 1);
        saveMapToFile();
    }
    
    private void saveMapToFile() {
        try (FileWriter fileWriter = new FileWriter(mapFile)) {
            String json = mapper.writeValueAsString(waitingThreadCountMap);
            fileWriter.write(json);
        } catch (IOException e) {
            log.error("Failed to save waiting thread count map to file", e);
        }
    }

    private void restoreMapFromFile() {
        try (FileReader fileReader = new FileReader(mapFile)) {
            Map<String, Integer> restoredMap = mapper.readValue(fileReader, new TypeReference<Map<String, Integer>>() {});
            waitingThreadCountMap.clear();
            waitingThreadCountMap.putAll(restoredMap);
        } catch (IOException e) {
            log.error("Failed to restore waiting thread count map from file", e);
        }
    
    }
}
