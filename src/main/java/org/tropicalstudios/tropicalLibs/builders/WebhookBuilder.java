package org.tropicalstudios.tropicalLibs.builders;

import org.bukkit.Color;
import org.tropicalstudios.tropicalLibs.utils.WebhookUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebhookBuilder {

    private final String webhookUrl;
    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;
    private final List<EmbedBuilder> embeds;

    public WebhookBuilder(String webhookUrl) {
        this.webhookUrl = webhookUrl;
        this.embeds = new ArrayList<>();
        this.tts = false;
    }

    public WebhookBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public WebhookBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public WebhookBuilder setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public WebhookBuilder setTts(boolean tts) {
        this.tts = tts;
        return this;
    }

    public WebhookBuilder addEmbed(EmbedBuilder embed) {
        if (embed != null) {
            this.embeds.add(embed);
        }
        return this;
    }

    public WebhookBuilder clearEmbeds() {
        this.embeds.clear();
        return this;
    }

    public EmbedBuilder createEmbed() {
        return new EmbedBuilder(this);
    }

    public void send() throws IOException {
        // Create a new DiscordWebhook instance directly
        WebhookUtils.DiscordWebhook webhook = new WebhookUtils.DiscordWebhook(webhookUrl);

        if (username != null) webhook.setUsername(username);
        if (avatarUrl != null) webhook.setAvatarUrl(avatarUrl);
        if (content != null) webhook.setContent(content);
        webhook.setTts(tts);

        // Add each embed individually
        for (EmbedBuilder embedBuilder : embeds) {
            webhook.addEmbed(embedBuilder.build());
        }

        webhook.execute();
    }

    public static class EmbedBuilder {
        private final WebhookBuilder parentBuilder;
        private String title;
        private String description;
        private String url;
        private Color color;
        private String footerText;
        private String footerIconUrl;
        private String thumbnailUrl;
        private String imageUrl;
        private String authorName;
        private String authorUrl;
        private String authorIconUrl;
        private final List<FieldData> fields;

        public EmbedBuilder(WebhookBuilder parentBuilder) {
            this.parentBuilder = parentBuilder;
            this.fields = new ArrayList<>();
        }

        public EmbedBuilder() {
            this.parentBuilder = null;
            this.fields = new ArrayList<>();
        }

        public EmbedBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public EmbedBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public EmbedBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public EmbedBuilder setColor(Color color) {
            this.color = color;
            return this;
        }

        public EmbedBuilder setColor(int red, int green, int blue) {
            this.color = Color.fromRGB(red, green, blue);
            return this;
        }

        public EmbedBuilder setColor(int rgb) {
            this.color = Color.fromRGB(rgb);
            return this;
        }

        public EmbedBuilder setFooter(String text) {
            this.footerText = text;
            this.footerIconUrl = null;
            return this;
        }

        public EmbedBuilder setFooter(String text, String iconUrl) {
            this.footerText = text;
            this.footerIconUrl = iconUrl;
            return this;
        }

        public EmbedBuilder setThumbnail(String url) {
            this.thumbnailUrl = url;
            return this;
        }

        public EmbedBuilder setImage(String url) {
            this.imageUrl = url;
            return this;
        }

        public EmbedBuilder setAuthor(String name) {
            this.authorName = name;
            this.authorUrl = null;
            this.authorIconUrl = null;
            return this;
        }

        public EmbedBuilder setAuthor(String name, String url) {
            this.authorName = name;
            this.authorUrl = url;
            this.authorIconUrl = null;
            return this;
        }

        public EmbedBuilder setAuthor(String name, String url, String iconUrl) {
            this.authorName = name;
            this.authorUrl = url;
            this.authorIconUrl = iconUrl;
            return this;
        }

        public EmbedBuilder addField(String name, String value) {
            return addField(name, value, false);
        }

        public EmbedBuilder addField(String name, String value, boolean inline) {
            if (name != null && value != null) {
                this.fields.add(new FieldData(name, value, inline));
            }
            return this;
        }

        public EmbedBuilder addInlineField(String name, String value) {
            return addField(name, value, true);
        }

        public EmbedBuilder clearFields() {
            this.fields.clear();
            return this;
        }

        public WebhookBuilder addToWebhook() {
            if (parentBuilder != null) {
                parentBuilder.addEmbed(this);
                return parentBuilder;
            }
            throw new IllegalStateException("This EmbedBuilder was not created from a WebhookBuilder");
        }

        public WebhookUtils.DiscordWebhook.EmbedObject build() {
            WebhookUtils.DiscordWebhook.EmbedObject embed = new WebhookUtils.DiscordWebhook.EmbedObject();

            if (title != null) embed.setTitle(title);
            if (description != null) embed.setDescription(description);
            if (url != null) embed.setUrl(url);
            if (color != null) embed.setColor(color);
            if (footerText != null) embed.setFooter(footerText, footerIconUrl);
            if (thumbnailUrl != null) embed.setThumbnail(thumbnailUrl);
            if (imageUrl != null) embed.setImage(imageUrl);
            if (authorName != null) embed.setAuthor(authorName, authorUrl, authorIconUrl);

            for (FieldData field : fields) {
                embed.addField(field.name, field.value, field.inline);
            }

            return embed;
        }

        private static class FieldData {
            final String name;
            final String value;
            final boolean inline;

            FieldData(String name, String value, boolean inline) {
                this.name = name;
                this.value = value;
                this.inline = inline;
            }
        }
    }
}
