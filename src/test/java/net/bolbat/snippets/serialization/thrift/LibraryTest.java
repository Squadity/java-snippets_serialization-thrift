package net.bolbat.snippets.serialization.thrift;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TCompactProtocol;
import org.junit.jupiter.api.Test;

import net.bolbat.snippets.serialization.thrift.library.Author;
import net.bolbat.snippets.serialization.thrift.library.Book;
import net.bolbat.snippets.serialization.thrift.library.Category;

public class LibraryTest {

	@Test
	public void exampleUsage() throws TException {
		final Author author = new Author()
				.setId(randomId())
				.setName("Yuval Noah Harari");
		final Category category = new Category()
				.setId(randomId())
				.setName("Anthropology");
		final Book book = new Book()
				.setId(randomId())
				.setName("Sapiens: A Brief History of Humankind");
		book.addToAuthors(author.getId());
		book.addToCategories(category.getId());

		final TSerializer serializer = new TSerializer(new TCompactProtocol.Factory());
		final byte[] serializedBook = serializer.serialize(book);

		final Book restoredBook = new Book();
		final TDeserializer deserializer = new TDeserializer(new TCompactProtocol.Factory());
		deserializer.deserialize(restoredBook, serializedBook);

		assertThat(restoredBook, equalTo(book));
		assertThat(restoredBook.getAuthors().size(), equalTo(1));
		assertThat(restoredBook.getAuthors(), contains(author.getId()));
		assertThat(restoredBook.getCategories().size(), equalTo(1));
		assertThat(restoredBook.getCategories(), contains(category.getId()));
	}

	private String randomId() {
		return UUID.randomUUID().toString();
	}

}
