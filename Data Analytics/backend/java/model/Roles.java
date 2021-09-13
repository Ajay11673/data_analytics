package model;

public enum Roles {
ADMIN("ADMIN"), GUEST("GUEST");
String role;
Roles(String role) {
	this.role = role;
}

public String getRole() {
	return role;
}
}
