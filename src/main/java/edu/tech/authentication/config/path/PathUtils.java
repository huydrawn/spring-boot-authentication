package edu.tech.authentication.config.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.tech.authentication.model.priority.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PathUtils {
	private List<String> permitAllPaths;
	private Map<Role, List<String>> requirdPaths;

	public PathUtils() {
		super();
		this.permitAllPaths = new ArrayList<>();
		this.requirdPaths = new HashMap<>();
	}

	public void addPath(Role role, String path) {
		if (this.getRequirdPaths().containsKey(role)) {
			this.getRequirdPaths().get(role).add(path);
		} else
			this.getRequirdPaths().put(role, permitAllPaths);
	}

	public void addPath(Role role, String... paths) {
		if (this.getRequirdPaths().containsKey(role)) {
			this.getRequirdPaths().get(role).addAll(Arrays.asList(paths));
		} else
			this.getRequirdPaths().put(role, Arrays.asList(paths));
	}

	public void addPathToPermitAll(String path) {
		this.getPermitAllPaths().add(path);
	}

	public void addPathToPermitAll(String... paths) {
		this.getPermitAllPaths().addAll(Arrays.asList(paths));
	}

	public String[] getRequirdsPathByRole(Role role) {
		return this.getRequirdPaths().get(role).toArray(new String[this.getRequirdPaths().get(role).size()]);
	}

	public String[] getPermitPaths() {
		return this.getPermitAllPaths().toArray(new String[this.getPermitAllPaths().size()]);
	}
}
