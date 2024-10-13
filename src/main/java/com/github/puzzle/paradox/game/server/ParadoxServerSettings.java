package com.github.puzzle.paradox.game.server;

import finalforeach.cosmicreach.networking.server.ServerSingletons;
import finalforeach.cosmicreach.networking.server.ServerZoneLoader;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.Objects;

public class ParadoxServerSettings {

    public static boolean doesC4Explode = true;
    public static boolean executeChatCommands = true;
    public static boolean canBreakBlock = true;
    public static boolean canPlaceBlock = true;
    public static String joinMessage = "";
    public static boolean canChat = true;

    public static boolean getBool(String name){
      return ServerSingletons.puzzle.serverConfig.get(Boolean.class,name,true);
    }
    public static int getInt(String name){
        return ServerSingletons.puzzle.serverConfig.get(Integer.class,name,0);
    }
    public static void writeSetting(){
        PropertiesConfiguration config = ServerSingletons.puzzle.serverConfig;
        config.addProperty("iteraction.shouldexplodec4",doesC4Explode);
        config.addProperty("iteraction.canbreakblock",canBreakBlock);
        config.addProperty("iteraction.canplaceblock",canPlaceBlock);
        config.addProperty("server.canchat",canChat);
        config.addProperty("commands.enabled",executeChatCommands);
        var rd = ServerZoneLoader.INSTANCE.serverLoadDistance;
        config.addProperty("server.renderdistance",rd < 3 || rd > 32 ? 10 : rd);
        try {
            ServerSingletons.puzzle.configBuilder.save();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
    public static void initSetting(){
        PropertiesConfiguration config = ServerSingletons.puzzle.serverConfig;

        doesC4Explode = getBool("iteraction.shouldexplodec4");
        canBreakBlock =  getBool("iteraction.canbreakblock");
        canPlaceBlock =  getBool("iteraction.canplaceblock");
        canChat = getBool("server.canchat");
        executeChatCommands =  getBool("commands.enabled");
        joinMessage = Objects.requireNonNullElse(config.getString("server.joinmessage"),"");
        var rd = getInt("server.renderdistance");
        ServerZoneLoader.INSTANCE.serverLoadDistance = rd < 3 || rd > 32 ? 10 : rd;

    }

}
