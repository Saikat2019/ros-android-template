package org.ollide.rosandroid;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import std_msgs.String;

public class Talker extends AbstractNodeMain {
    private java.lang.String topic_name;

    public Talker() {
        this.topic_name = "chatter";
    }

    public Talker(java.lang.String topic) {
        this.topic_name = topic;
    }

    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava_tutorial_pubsub/talker");
    }

    public void onStart(ConnectedNode connectedNode) {
        final Publisher<String> publisher = connectedNode.newPublisher(this.topic_name, "std_msgs/String");
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            private int sequenceNumber;

            protected void setup() {
                this.sequenceNumber = 0;
            }

            protected void loop() throws InterruptedException {
                std_msgs.String str = (std_msgs.String)publisher.newMessage();
                str.setData("Hello world! " + this.sequenceNumber);
                publisher.publish(str);
                ++this.sequenceNumber;
                Thread.sleep(1000L);
            }
        });
    }
}
