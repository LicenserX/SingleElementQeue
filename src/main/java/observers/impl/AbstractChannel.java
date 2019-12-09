package observers.impl;

public abstract class AbstractChannel {

    private String uniqueChannelName;

    AbstractChannel(String uniqueChannelName) {
        this.uniqueChannelName = uniqueChannelName;
    }

    public String getUniqueChannelName() {
        return uniqueChannelName;
    }

    public void setUniqueChannelName(String uniqueChannelName) {
        this.uniqueChannelName = uniqueChannelName;
    }
}
