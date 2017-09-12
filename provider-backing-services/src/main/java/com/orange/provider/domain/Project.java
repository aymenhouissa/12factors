package com.orange.provider.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


public @Data @RequiredArgsConstructor class Project {
	private @NonNull String name;
    private @NonNull String version;
    
}
