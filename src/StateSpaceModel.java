public interface StateSpaceModel {
    Object action(Object o);
    void allActions();
    Object result();
    Object state();
    int cost();
    boolean goal();
}
