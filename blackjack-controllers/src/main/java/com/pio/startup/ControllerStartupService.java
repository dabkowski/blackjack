package com.pio.startup;

import javafx.scene.input.MouseEvent;

public interface ControllerStartupService {

    void run();

    void handleActionEvent(MouseEvent event);

    void leaveInfoScreen(MouseEvent event);
    void hit(MouseEvent event);
    void leaveGame(MouseEvent event);
    void openInfoScreen(MouseEvent event);
    void stand(MouseEvent event);
    void add1000Chip(MouseEvent event);
    void add500Chip(MouseEvent event);
    void add200Chip(MouseEvent event);
    void add100Chip(MouseEvent event);
    void add50Chip(MouseEvent event);
    void add20Chip(MouseEvent event);
    void add10Chip(MouseEvent event);
}
