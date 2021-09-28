/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package sirius.kernel.xml;

import javax.xml.namespace.NamespaceContext;
import java.io.IOException;
import java.net.URI;

/**
 * Simple call to send XML to a server (URL) and receive XML back.
 */
public class XMLCall {

    private Outcall outcall;
    private NamespaceContext namespaceContext;

    /**
     * Creates a new XMLCall for the given url with Content-Type 'text/xml'.
     *
     * @param url the target URL to call
     * @return an <tt>XMLCall</tt> which can be used to send and receive XML
     * @throws IOException in case of an IO error
     */
    public static XMLCall to(URI url) throws IOException {
        return to(url, "text/xml");
    }

    /**
     * Creates a new XMLCall for the given url.
     *
     * @param url         the target URL to call
     * @param contentType the Content-Type to use
     * @return a new instance to perform the xml call
     * @throws IOException in case of an IO error
     */
    public static XMLCall to(URI url, String contentType) throws IOException {
        XMLCall result = new XMLCall();
        result.outcall = new Outcall(url);
        result.outcall.setRequestProperty("Content-Type", contentType);
        return result;
    }

    /**
     * Adds a custom header field to the call
     *
     * @param name  name of the field
     * @param value value of the field
     * @return returns the XML call itself for fluent method calls
     */
    public XMLCall addHeader(String name, String value) {
        outcall.setRequestProperty(name, value);
        return this;
    }

    /**
     * Specifies the <tt>NamespaceContext</tt> to use for the result of this call.
     * <p>
     * This is required to properly execute XPATH expressions on namespaced XMLs.
     *
     * @param namespaceContext the namespace context to use
     * @return the call itself for fluent method calls
     */
    public XMLCall withNamespaceContext(NamespaceContext namespaceContext) {
        this.namespaceContext = namespaceContext;
        return this;
    }

    /**
     * Returns the underlying <tt>Outcall</tt>.
     *
     * @return the underlying outcall
     */
    public Outcall getOutcall() {
        return outcall;
    }

    /**
     * Can be used to generate the XML request.
     * <p>
     * This will mark the underlying {@link Outcall} as a POST request.
     *
     * @return the output which can be used to generate an XML document which is sent to the URL
     * @throws IOException in case of an IO error while sending the XML document
     */
    public XMLStructuredOutput getOutput() throws IOException {
        outcall.markAsPostRequest();
        return new XMLStructuredOutput(outcall.getOutput());
    }

    /**
     * Provides access to the XML answer of the call.
     *
     * @return the XML result of the call
     * @throws IOException in case of an IO error while receiving the result
     */
    public XMLStructuredInput getInput() throws IOException {
        return new XMLStructuredInput(outcall.callForInputStream().body(), namespaceContext);
    }
}
