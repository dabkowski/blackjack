package com.pio.startup;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

@Component
public class BaseControllerStartupService implements ControllerStartupService {

    private final ViewStartupService viewStartupService;

    public BaseControllerStartupService(ViewStartupService viewStartupService) {
        this.viewStartupService = viewStartupService;
    }

    @Override
    public void run() {
        viewStartupService.showView();
    }

    public void handleActionEvent(MouseEvent event) {
        System.out.println("Button pressed!");
    }

    public void leaveInfoScreen(MouseEvent event) {
        System.out.println("Leaving!");
    }

    public void hit(MouseEvent event){System.out.println("Hit");}
    public void leaveGame(MouseEvent event){System.out.println("leaveGame");}
    public void openInfoScreen(MouseEvent event){System.out.println("openInfoScreen");}

    public void stand(MouseEvent event){System.out.println("stand");}
    public void add1000Chip(MouseEvent event){System.out.println("add1000Chip");}
    public void add500Chip(MouseEvent event){System.out.println("add500Chip");}
    public void add200Chip(MouseEvent event){System.out.println("add200Chip");}
    public void add100Chip(MouseEvent event){System.out.println("add100Chip");}
    public void add50Chip(MouseEvent event){System.out.println("add50Chip");}
    public void add20Chip(MouseEvent event){System.out.println("add20Chip");}
    public void add10Chip(MouseEvent event){System.out.println("add10Chip");}






}
