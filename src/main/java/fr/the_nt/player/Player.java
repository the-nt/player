package fr.the_nt.player;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.the_nt.player.Bac;
import net.md_5.bungee.event.EventHandler;


public class Player extends Plugin implements Listener {

    final String username="u1_YU0yzkgixm"; // Enter in your db username
    final String password="Mv!jWf.0GBY46!P0zCdWaAmI"; // Enter your password for the db
    final String url = "jdbc:mysql://panel.city-of-nations.com:3306/s1_con"; // Enter URL with db name

    //Connection vars
    static Connection connection; //This is the variable we will use to connect to database

    @Override
    public void onEnable(){
        getLogger().info("Start player");
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Bac(this));
        getProxy().getPluginManager().registerListener(this, this);
        try { // try catch to get any SQL errors (for example connections errors)
            connection = DriverManager.getConnection(url, username, password);
            // with the method getConnection() from DriverManager, we're trying to set
            // the connection's url, username, password to the variables we made earlier and
            // trying to get a connection at the same time. JDBC allows us to do this.
        } catch (SQLException e) { // catching errors
            e.printStackTrace(); // prints out SQLException errors to the console (if any)
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Stop player");
        // invoke on disable.
        try { // using a try catch to catch connection errors (like wrong sql password...)
            if (connection!=null && !connection.isClosed()){ // checking if connection isn't null to
                // avoid receiving a nullpointer
                connection.close(); // closing the connection field variable.
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onJoin(PostLoginEvent player) throws SQLException {
        String sql = "INSERT INTO Bungee_ips VALUES (?,?,?) ON DUPLICATE KEY UPDATE ip=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, player.getPlayer().getDisplayName());
        stmt.setString(2, player.getPlayer().getUniqueId().toString());
        stmt.setString(3, player.getPlayer().getAddress().getAddress().getHostAddress());
        stmt.setString(4, player.getPlayer().getAddress().getAddress().getHostAddress());
        stmt.executeUpdate();
        String sql2 = "INSERT INTO Bungee_player(nbrplayer) VALUES (?)";
        PreparedStatement stmt2 = connection.prepareStatement(sql2);
        stmt2.setInt(1, getProxy().getOnlineCount());
        stmt2.executeUpdate();
    }
}
