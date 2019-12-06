package observers.impl;

public abstract class AbstractChannel {

    private String uniqueChannelName;

    public AbstractChannel(String uniqueChannelName) {
        this.uniqueChannelName = uniqueChannelName;
    }

    public String getUniqueChannelName() {
        return uniqueChannelName;
    }

    public void setUniqueChannelName(String uniqueChannelName) {
        this.uniqueChannelName = uniqueChannelName;
    }
}
