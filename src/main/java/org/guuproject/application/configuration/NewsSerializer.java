package org.guuproject.application.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.guuproject.application.models.News;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
public class NewsSerializer extends JsonSerializer<List<News>> {
    @Override
    public void serialize(List<News> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        for (News news:value){
            news.getWriter().setNews(null);
        }
        gen.writeObject(value);
    }
}
