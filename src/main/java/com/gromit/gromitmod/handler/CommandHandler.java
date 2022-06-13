package com.gromit.gromitmod.handler;

import com.gromit.gromitmod.commands.CalculatorCommand;
import com.gromit.gromitmod.commands.CannonCalculateCommand;
import net.minecraftforge.client.ClientCommandHandler;

public class CommandHandler {

    public CommandHandler() {
        ClientCommandHandler clientCommandHandler = ClientCommandHandler.instance;
        clientCommandHandler.registerCommand(new CannonCalculateCommand());
        clientCommandHandler.registerCommand(new CalculatorCommand());
    }
}
