package study.data.jpa.repository;

public interface NestedClosedProjections {

  String getUsername();

  TeamInfo getTeam();

  interface TeamInfo {
    String getName();
  }
}
