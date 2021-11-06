```
@GenericGenerator
```
It is used to generate the unique identifier for the objects of persistent class. 
There are many generator classes defined in the Hibernate Framework.<br>

All the generator classes implements the org.hibernate.id.IdentifierGenerator interface. 
The application programmer may create one's own generator classes by implementing the **IdentifierGenerator** interface. 
Hibernate framework provides many built-in generator classes:
1. assigned
2. increment
3. sequence
4. hilo
5. native
6. identity
7. seqhilo
8. uuid
9. guid
10. select
11. foreign
12. sequence-identity

```
private Collection<Address> addressSet = new ArrayList();

@ElementCollection
	@CollectionTable (name = "STUDENT_ADDRESS", joinColumns = @JoinColumn(name = "STUDENT_ID"))
	@GenericGenerator(name = "sequenceGen", strategy = "sequence")
	@CollectionId(columns = { @Column (name = "ADDRESS_ID")}, generator = "sequenceGen", type = @Type(type="long"))
	public Collection<Address> getAddressSet() {
		return addressSet;
	}
```

* `@CollectionId` tells that `addressSet` should have an identifier. And its properties `@Column (name = "ADDRESS_ID")` defines what should be the identifier column name,`generator = "sequenceGen"` defines ehat strategy to be used to generate the identifiers and `type = @Type(type="long")` what should be the type of the identifier
* `@GenericGenerator` tells what strategy to be used to generate the identifiers. And its properties `name = "sequenceGen"` defines the name and `strategy = "sequence"` defines what should be the strategy to be used to generate identifiers

 


