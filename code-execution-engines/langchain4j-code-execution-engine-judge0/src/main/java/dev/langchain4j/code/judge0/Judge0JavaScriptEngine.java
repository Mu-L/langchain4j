package dev.langchain4j.code.judge0;

import dev.langchain4j.code.CodeExecutionEngine;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Base64;

import static dev.langchain4j.internal.Utils.isNullOrBlank;

class Judge0JavaScriptEngine implements CodeExecutionEngine {

    private static final Logger log = LoggerFactory.getLogger(Judge0JavaScriptEngine.class);

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    private static final int ACCEPTED = 3;

    private final String apiKey;
    private final int languageId;
    private final OkHttpClient client;

    Judge0JavaScriptEngine(String apiKey, int languageId, Duration timeout) {
        this.apiKey = apiKey;
        this.languageId = languageId;
        this.client = new OkHttpClient.Builder()
            .connectTimeout(timeout)
            .readTimeout(timeout)
            .writeTimeout(timeout)
            .callTimeout(timeout)
            .build();
    }

    @Override
    public String execute(String code) {

        String base64EncodedCode = Base64.getEncoder().encodeToString(code.getBytes());

        Submission submission = new Submission(languageId, base64EncodedCode);

        RequestBody requestBody = RequestBody.create(Json.toJson(submission), MEDIA_TYPE);

        Request request = new Request.Builder()
            .url("https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=true&wait=true&fields=*")
            .addHeader("X-RapidAPI-Key", apiKey)
            .post(requestBody)
            .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            SubmissionResult result = Json.fromJson(responseBody, SubmissionResult.class);

            if (result.status.id != ACCEPTED) {
                String error = result.status.description;
                if (!isNullOrBlank(result.compile_output)) {
                    error += "\n";
                    error += new String(Base64.getMimeDecoder().decode(result.compile_output));
                }
                return error;
            }

            String base64EncodedStdout = result.stdout;
            if (base64EncodedStdout == null) {
                return "No result: nothing was printed out to the console";
            }

            return new String(Base64.getMimeDecoder().decode(base64EncodedStdout.trim())).trim();
        } catch (Exception e) {
            log.warn("Error during code execution", e);
            return e.getMessage();
        }
    }

    private static class Submission {

        int language_id;
        String source_code;

        Submission(int languageId, String sourceCode) {
            this.language_id = languageId;
            this.source_code = sourceCode;
        }

        public int getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(int language_id) {
            this.language_id = language_id;
        }

        public String getSource_code() {
            return source_code;
        }

        public void setSource_code(String source_code) {
            this.source_code = source_code;
        }
    }

    private static class SubmissionResult {

        String stdout;
        Status status;
        String compile_output;

        public String getStdout() {
            return stdout;
        }

        public void setStdout(String stdout) {
            this.stdout = stdout;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public String getCompile_output() {
            return compile_output;
        }

        public void setCompile_output(String compile_output) {
            this.compile_output = compile_output;
        }
    }

    private static class Status {

        int id;
        String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
