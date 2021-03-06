package haveric.snowballStacker;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.state.ServerAboutToStartEvent;
import org.spongepowered.api.event.state.ServerStartingEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.api.service.event.EventManager;
import org.spongepowered.api.util.event.Subscribe;

import com.google.common.base.Optional;

@Plugin(id = "SnowballStacker", name = "Snowball Stacker", version = "2.0")
public class SnowballStacker {
    private Logger log;

    private Game game;
    @SuppressWarnings("unused")
    private Commands commands;
    private Config config;

    @Subscribe
    public void preStartup(ServerAboutToStartEvent event) {
        game = event.getGame();
        PluginManager pm = game.getPluginManager();

        Optional<PluginContainer> optionalContainer = pm.fromInstance(this);
        if (optionalContainer.isPresent()) {
            PluginContainer pc = optionalContainer.get();
            log = pm.getLogger(pc);
        }
    }

    @Subscribe
    public void onStartup(ServerStartingEvent event) {
        EventManager em = game.getEventManager();
        em.register(this, new SBPlayerInteract(this));

        commands = new Commands(this);
        config = new Config(this);
    }

    @Subscribe
    public void onShutdown(ServerStoppingEvent event) {

    }

    public Game getGame() {
        return game;
    }

    public Logger getLog() {
        return log;
    }

    // TODO: Replace with Plugin annotation's id/name when possible
    public String getName() {
        return "SnowballStacker";
    }

    // TODO: Replace with Plugin annotation's version when possible
    public String getVersion() {
        return "2.0";
    }
}
