package com.example.securitytesttoken.serviceImpl;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.securitytesttoken.service.CpuMonitoringService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CpuMonitoringServiceImpl implements CpuMonitoringService {
	
	private final int CPU_THRESHOLD = 80; // %
	private final int MEMORY_THRESHOLD_MB = 500; // MB

	private OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    private MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	
	@Override
	public Map<String, Object> monitoring() {
		// 메모리 사용량
		double usedMemory = memoryMXBean.getHeapMemoryUsage().getUsed() / (1024*1024);
		if(usedMemory > MEMORY_THRESHOLD_MB) {
			log.warn("메모리 입계값 초과 >>> 사용량 {}MB", usedMemory);
			// 관리자 전원에게 메일 보내기
		}
		
		// JVM프로세스가 사용하는 CPU의 백분율 ( 잘 활용하면 병목현상을 찾을 수 있다. )
		double cpuUsage = ((com.sun.management.OperatingSystemMXBean)osBean).getProcessCpuLoad() * 100;
        if(cpuUsage > CPU_THRESHOLD) {
        	log.warn("cpu 사용량 임계값 초과 >>> 사용량 : {}%", cpuUsage);
        	// 관리자 전원에게 메시지 보내기
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("Memory", usedMemory + "MB");
        result.put("CPU", cpuUsage + "%");
        
        return result;
	}
	
}
